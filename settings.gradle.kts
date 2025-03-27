rootProject.name = "SimpleProxyChat"

include(
    "projects/proxy/bungeecord",
    "projects/proxy/velocity",
    "projects/proxy/util",

    "projects/client/spigot",
    "projects/client/paper",
    "projects/client/util",
)

project(":projects/proxy/bungeecord").name = "SimpleProxyChat-Bungeecord"
project(":projects/proxy/velocity").name = "SimpleProxyChat-Velocity"
project(":projects/proxy/util").name = "SimpleProxyChat-ProxyUtil"

project(":projects/client/spigot").name = "SimpleProxyChatHelper-Spigot"
project(":projects/client/paper").name = "SimpleProxyChatHelper-Paper"
project(":projects/client/util").name = "SimpleProxyChatHelper-ClientUtil"
