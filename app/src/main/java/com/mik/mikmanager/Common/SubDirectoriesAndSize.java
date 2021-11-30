package com.mik.mikmanager.Common;


import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SubDirectoriesAndSize {

    final public long size;
    final public List<File> subDirectories;

    public SubDirectoriesAndSize(final long totalSize,
                                 final List<File> theSubDirs) {
        size = totalSize;
        subDirectories = Collections.unmodifiableList(theSubDirs);
    }



}