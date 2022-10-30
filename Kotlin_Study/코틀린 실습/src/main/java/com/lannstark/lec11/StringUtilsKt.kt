package com.lannstark.lec11

    // 유틸성 함수를 따로 파일로 만들어서 사용하는 것이 편함
    fun isDirectoryPath(path: String): Boolean {
        return path.endsWith("/")
    }

