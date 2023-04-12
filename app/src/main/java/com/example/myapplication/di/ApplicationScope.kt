package com.example.myapplication

import javax.inject.Scope



//defines the scope for objects that are available  throughout the entire application.
// This annotation could be used to
// annotate objects that provide global dependencies for the application
@Scope
@Retention
annotation class ApplicationScope


//dependencies for the repository layer, such as database or network access objects.
@Scope
@Retention
annotation class RepositoryScope


// for setting page
@Scope
@Retention
annotation class SettingPageScope