rootProject.name = "SimpleProxyChat"

include(
    "projects/standalone",
    "projects/helper"
)

project(":projects/standalone").name = "SimpleProxyChat"
project(":projects/helper").name = "SimpleProxyChatHelper"
