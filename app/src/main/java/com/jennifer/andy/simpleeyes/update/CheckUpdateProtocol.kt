package com.jennifer.andy.simpleeyes.update

import android.os.Parcel
import android.os.Parcelable
import android.text.TextUtils
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Author:  andy.xwt
 * Date:    2017/10/31 11:36
 * Description: 检测更新协议
 */

class CheckUpdateProtocol() : Parcelable, Serializable {

    @SerializedName("app_name")
    var appName: String? = null
    @SerializedName("installage")
    var installedDays = -1L
    @SerializedName("is_oem")
    var isOem = false
    @SerializedName("language")
    var language: String? = null
    @SerializedName("net_subtype")
    var netSubtype: String? = null
    @SerializedName("net_type")
    var netType: String? = null
    @SerializedName("package_name")
    var packageName: String? = null
    @SerializedName("protocol_version")
    var protocolVersion = "1.0.0.1"
    @SerializedName("rom")
    var rom: String? = null
    @SerializedName("rom_version")
    var romVersion: String? = null
    @SerializedName("source")
    var source: String? = null
    @SerializedName("udid")
    var udid: String? = null
    @SerializedName("version_code")
    var versionCode = -1
    @SerializedName("version_name")
    var versionName: String? = null

    constructor(parcel: Parcel) : this() {
        appName = parcel.readString()
        installedDays = parcel.readLong()
        isOem = parcel.readByte() != 0.toByte()
        language = parcel.readString()
        netSubtype = parcel.readString()
        netType = parcel.readString()
        packageName = parcel.readString()
        protocolVersion = parcel.readString()
        rom = parcel.readString()
        romVersion = parcel.readString()
        source = parcel.readString()
        udid = parcel.readString()
        versionCode = parcel.readInt()
        versionName = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(appName)
        parcel.writeLong(installedDays)
        parcel.writeByte(if (isOem) 1 else 0)
        parcel.writeString(language)
        parcel.writeString(netSubtype)
        parcel.writeString(netType)
        parcel.writeString(packageName)
        parcel.writeString(protocolVersion)
        parcel.writeString(rom)
        parcel.writeString(romVersion)
        parcel.writeString(source)
        parcel.writeString(udid)
        parcel.writeInt(versionCode)
        parcel.writeString(versionName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CheckUpdateProtocol> {
        override fun createFromParcel(parcel: Parcel): CheckUpdateProtocol {
            return CheckUpdateProtocol(parcel)
        }

        override fun newArray(size: Int): Array<CheckUpdateProtocol?> {
            return arrayOfNulls(size)
        }
    }

    /**
     * 判断是否合法(从version,appName,packageName,versionName,versionCode,language中判断）
     */
    fun isValid(): Boolean {
        return (!TextUtils.isEmpty(this.protocolVersion)) &&
                (!TextUtils.isEmpty(this.appName)) &&
                (!TextUtils.isEmpty(this.packageName)) &&
                (!TextUtils.isEmpty(this.versionName)) &&
                (this.versionCode != -1) &&
                (!TextUtils.isEmpty(this.language))
    }

}
