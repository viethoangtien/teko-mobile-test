package com.beetech.productmanagement.di.annotation

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
annotation class LayoutId(val value: Int)