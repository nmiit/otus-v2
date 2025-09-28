package com.example.test_kuber.security.rule

import org.springframework.security.access.prepost.PreAuthorize

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention
@PreAuthorize("hasRole('USER')")
annotation class HasUserRole

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention
@PreAuthorize("hasRole('ADMIN')")
annotation class HasAdminRole

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
annotation class HasAnyRole