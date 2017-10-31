package com.jennifer.andy.simpleeyes.update

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.io.Serializable


/**
 * Author:  andy.xwt
 * Date:    2017/10/31 11:30
 * Description:
 */

class LocalUpdateService : Service() {


    var mUpdateParams: UpdateParams? = null

    override fun onBind(intent: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    //todo 联网检查更新

    class UpdateParams : Serializable {
        var checkUpdateProtocol: CheckUpdateProtocol? = null
        var downloadInstallerOnlyOnWifi = true
        var nocifiactionIcon = -1
        var updateDelayMs = 120000L
        var updateDurationMs = 7200000L
    }

}