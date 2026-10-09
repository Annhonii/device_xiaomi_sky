/*
 * Copyright (C) 2021-2022 The LineageOS Project
 *
 * SPDX-License-Identifier: Apache-2.0
 */

#define _REALLY_INCLUDE_SYS__SYSTEM_PROPERTIES_H_
#include <sys/_system_properties.h>
#include <sys/sysinfo.h>
#include <cstdint>
#include <string>

#include "vendor_init.h"

static void property_override(const std::string& prop, const std::string& value, bool add = true) {
    auto pi = (prop_info*) __system_property_find(prop.c_str());
    if (pi != nullptr) {
        __system_property_update(pi, value.c_str(), value.length());
    } else if (add) {
        __system_property_add(prop.c_str(), prop.length(), value.c_str(), value.length());
    }
}

void set_dalvik_heap() {
    struct sysinfo sys;

    if (sysinfo(&sys) != 0) {
        return;
    }

    uint64_t total_ram = static_cast<uint64_t>(sys.totalram) * sys.mem_unit;

    if (total_ram >= (8ULL * 1024 * 1024 * 1024)) {
        // phone-xhdpi-8192-dalvik-heap.mk
        property_override("dalvik.vm.heapstartsize", "24m");
        property_override("dalvik.vm.heapgrowthlimit", "256m");
        property_override("dalvik.vm.heapsize", "512m");
        property_override("dalvik.vm.heaptargetutilization", "0.46");
        property_override("dalvik.vm.heapminfree", "8m");
        property_override("dalvik.vm.heapmaxfree", "48m");
    } else if (total_ram >= (6ULL * 1024 * 1024 * 1024)) {
        // phone-xhdpi-6144-dalvik-heap.mk
        property_override("dalvik.vm.heapstartsize", "16m");
        property_override("dalvik.vm.heapgrowthlimit", "256m");
        property_override("dalvik.vm.heapsize", "512m");
        property_override("dalvik.vm.heaptargetutilization", "0.5");
        property_override("dalvik.vm.heapminfree", "8m");
        property_override("dalvik.vm.heapmaxfree", "32m");
    } else {
        // phone-xhdpi-4096-dalvik-heap.mk
        property_override("dalvik.vm.heapstartsize", "8m");
        property_override("dalvik.vm.heapgrowthlimit", "192m");
        property_override("dalvik.vm.heapsize", "512m");
        property_override("dalvik.vm.heaptargetutilization", "0.6");
        property_override("dalvik.vm.heapminfree", "8m");
        property_override("dalvik.vm.heapmaxfree", "16m");
    }
}

void set_bg_apps_limit() {
    struct sysinfo sys;

    if (sysinfo(&sys) != 0)
        return;

    uint64_t total_ram =
        static_cast<uint64_t>(sys.totalram) * sys.mem_unit;

    if (total_ram >= (6ULL * 1024 * 1024 * 1024))
        property_override("ro.sys.fw.bg_apps_limit", "28");
    else
        property_override("ro.sys.fw.bg_apps_limit", "24");
}

void set_swappiness() {
    struct sysinfo sys;

    if (sysinfo(&sys) != 0) {
        return;
    }

    uint64_t total_ram = static_cast<uint64_t>(sys.totalram) * sys.mem_unit;

    if (total_ram >= (5ULL * 1024 * 1024 * 1024)) {
        property_override("ro.vendor.qti.config.swappiness", "60");
    } else {
        property_override("ro.vendor.qti.config.swappiness", "40");
    }
}

void vendor_load_properties() {
    set_dalvik_heap();
    set_bg_apps_limit();
    set_swappiness();
}
