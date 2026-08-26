#
# Copyright (C) 2023 The Android Open Source Project
#
# SPDX-License-Identifier: Apache-2.0
#

PRODUCT_SYSTEM_NAME := sky_global
PRODUCT_SYSTEM_DEVICE := sky

# Call the MiuiCamera setup
$(call soong_config_set,camera,package_name,com.xiaomi.sessionparams.clientName)

PRODUCT_BUILD_PROP_OVERRIDES += \
    BuildDesc="sky_global-user 14 UKQ1.231003.002 OS2.0.210.0.VMWMIXM release-keys" \
    BuildFingerprint=Redmi/sky/sky:14/UKQ1.231003.002/OS2.0.210.0.VMWMIXM:user/release-keys \
    DeviceName=$(PRODUCT_SYSTEM_DEVICE) \
    DeviceProduct=sky


PRODUCT_GMS_CLIENTID_BASE := android-xiaomi
