package com.amarnehsoft.holyquran.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;
import javax.inject.Scope;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface Tafseer {
}
