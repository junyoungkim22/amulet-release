suite = {
    "name": "vec",
    "mxversion": "5.272.0",

    "libraries": {
        "FREQUENCY": {
            "maven": {
                "groupId": "com.github.mayconbordin",
                "artifactId": "streaminer",
                "version": "1.1.1",
            },
            "sha1": "e0497fe4c084ca85057d48de87ffe3d7df1fe442",
        },
        "POWERLAWS": {
            "urls": ["https://jitpack.io/com/github/Data2Semantics/powerlaws/v0.1.0/powerlaws-v0.1.0.jar"],
            "sha1": "1cc6a9a34d815a1cfa2f91ccebffc44ec67bb092",
        },
        "JNA": {
            "maven": {
                "groupId": "net.java.dev.jna",
                "artifactId": "jna",
                "version": "5.6.0",
            },
            "sha1": "330f2244e9030119ab3030fc3fededc86713d9cc",
        },
        "COMMONS-LANG": {
            "maven": {
                "groupId": "commons-lang",
                "artifactId": "commons-lang",
                "version": "2.6",
            },
            "sha1": "0ce1edb914c94ebc388f086c6827e8bdeec71ac2",
        },
        "SLF4J_API": {
            "maven": {
                "groupId": "org.slf4j",
                "artifactId": "slf4j-api",
                "version": "1.7.26",
            },
            "sha1": "77100a62c2e6f04b53977b9f541044d7d722693d",
        },
        "SLF4J_SIMPLE": {
            "maven": {
                "groupId": "org.slf4j",
                "artifactId": "slf4j-simple",
                "version": "1.7.26",
            },
            "sha1": "dfb0de47f433c2a37dd44449c88d84b698cd5cf7",
        },
        "JOL": {
            "maven": {
                "groupId": "org.openjdk.jol",
                "artifactId": "jol-core",
                "version": "0.16",
            },
            "sha1": "553a2ba27f58b71e7efb545d7d3c657761f5b596",
        },
    },

    "projects": {
        "com.oracle.truffle.vec": {
            "subDir": "src",
            "sourceDirs": ["src"],
            "dependencies": [
                "graal-js:GRAALJS",
                "graal-js:GRAALJS_LAUNCHER",
                "FREQUENCY",
                "POWERLAWS",
                "JNA",
                "COMMONS-LANG",
                "SLF4J_API",
                "SLF4J_SIMPLE",
                "truffle:ANTLR4",
                "JOL",
            ],
            "annotationProcessors": ["truffle:TRUFFLE_DSL_PROCESSOR"],
            "javaCompliance": "8+",
        },
        "com.oracle.truffle.vec.test": {
            "subDir": "src",
            "sourceDirs": ["src"],
            "dependencies": [
                "mx:JUNIT",
            ],
            "javaCompliance": "8+",
        },
    },

    "distributions": {
        "VEC": {
            "subDir": "src",
            "dependencies": [
                "com.oracle.truffle.vec",
            ],
            "distDependencies": [
                "graal-js:GRAALJS",
                "graal-js:GRAALJS_LAUNCHER",
                "FREQUENCY",
                "POWERLAWS",
                "SLF4J_API",
                "SLF4J_SIMPLE",
            ],
            "description": "VecLang",
            "allowsJavadocWarnings": True,
        },
        "VEC_TEST": {
            "subDir": "src",
            "dependencies": [
                "com.oracle.truffle.vec.test",
            ],
            "distDependencies": [
                "VEC",
            ],
            "exclude": [
                "mx:JUNIT",
            ],
        },
    },
}
