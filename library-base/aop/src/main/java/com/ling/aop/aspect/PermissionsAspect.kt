package com.ling.aop.aspect

import android.app.Activity
import com.ling.aop.annotation.Permissions
import com.ling.common.manager.ActivityManager
import com.ling.gateway.custom.PermissionCallback
import com.ling.permissions.XXPermissions
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import timber.log.Timber

/**
 * author : wangchengzhen
 * github : https://github.com/getActivity/AndroidProject-Kotlin
 * time   : 2022/6/7
 * desc   : 权限申请切面
 */
@Suppress("unused")
@Aspect
class PermissionsAspect {

    /**
     * 方法切入点
     */
    @Pointcut("execution(@com.ling.aop.annotation.Permissions * *(..))")
    fun method() {
    }

    /**
     * 在连接点进行方法替换
     */
    @Around("method() && @annotation(permissions)")
    fun aroundJoinPoint(joinPoint: ProceedingJoinPoint, permissions: Permissions) {
        var activity: Activity? = null

        // 方法参数值集合
        val parameterValues: Array<Any?> = joinPoint.args
        for (arg: Any? in parameterValues) {
            if (arg !is Activity) {
                continue
            }
            activity = arg
            break
        }
        if ((activity == null) || activity.isFinishing || activity.isDestroyed) {
            activity = ActivityManager.getInstance().getTopActivity()
        }
        if ((activity == null) || activity.isFinishing || activity.isDestroyed) {
            Timber.e("The activity has been destroyed and permission requests cannot be made")
            return
        }
        requestPermissions(joinPoint, activity, permissions.value)
    }

    private fun requestPermissions(joinPoint: ProceedingJoinPoint, activity: Activity, permissions: Array<out String>) {
        XXPermissions.with(activity)
            .permission(*permissions)
            .request(object : PermissionCallback() {
                override fun onGranted(permissions: MutableList<String?>?, all: Boolean) {
                    if (all) {
                        try {
                            // 获得权限，执行原方法
                            joinPoint.proceed()
                        } catch (e: Throwable) {
                            e.printStackTrace()
                        }
                    }
                }
            })
    }
}
