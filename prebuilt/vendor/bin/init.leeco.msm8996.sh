#!/vendor/bin/sh
dev_info_file="/dev/block/sde21"
if [ -e $dev_info_file ]; then
    dev_info=`cat $dev_info_file`
    case $dev_info in
    "le_zl0_whole_netcom")
        setprop ro.product.device le_zl0
        setprop ro.vendor.product.model LEX722
        setprop persist.data.iwlan.enable false
        setprop ro.power_profile.override power_profile_zl0
        ;;
    "le_zl1_oversea")
        setprop ro.product.device le_zl1
        setprop ro.vendor.product.model LEX727
        setprop ro.vendor.product.name ZL1_NA
        setprop persist.data.iwlan.enable true
        # NFC
        setprop persist.nfc.smartcard.config SIM1,eSE1
        ;;
    "le_zl1")
        setprop ro.product.device le_zl1
        setprop ro.vendor.product.model LEX720
        setprop persist.data.iwlan.enable false
        # NFC
        setprop persist.nfc.smartcard.config SIM1,SIM2,eSE1
        ;;
    "le_x2_na_oversea")
        setprop ro.vendor.product.model LEX829
        ;;
    "le_x2_india")
        setprop ro.vendor.product.model LEX821
        ;;
    "le_x2")
        setprop ro.vendor.product.model LEX820
        ;;
    *)
        setprop ro.product.model UNKNOWN
        ;;
    esac
    # Single SIM model
    if [ $dev_info == "le_zl1_oversea" ]; then
        setprop persist.radio.multisim.config NA
        setprop ro.telephony.default_network 10
    else
        setprop persist.radio.multisim.config dsds
        setprop ro.telephony.default_network 10,10
    fi
else
    setprop ro.product.model UNKNOWN
fi
