# TimedRewards
###### The ultimate timed rewards plugin with no clutter.
---

This plugin gives you the ability to create unlimited TimeRewards, providing no UI the plugin registers a bunch of placeholders that give you full control over it, utilizing plugins like [DeluxeMenus](https://www.spigotmc.org/resources/deluxemenus.11734/) 

You can download this plugin off of spigot [here](https://www.spigotmc.org/resources/timed-rewards-%E2%9C%A8player-rewards.119467/)!

##### Default Config:
```yaml
# This is sent to the client when a reward is claimed.
rewardMessage: 'You have received %reward% reward from %name%' 

# This is the message sent to the player when they're on cooldown.
cooldownMessage: 'This rewards is still on cooldown for %remaining%'

# This is the message sent to the player when they don't have the permission needed to claim a reward.
noPermissionMessage: 'You do not have the permission required to claim this reward.'
```


##### Timed Reward Config:

_This is the default config generated when you start the plugin for the first time._

```yaml
displayName: Daily Reward # The DisplayName used when sending the players the `rewardMessage` 

claimEveryXSeconds: 86400 # This is how long a player would have to wait to claim again

permission: timedrewards.daily # The permission needed to claim this reward

giveXRewards: 1 # How many rewards the player should be given from the pool below

# The entries for the pool, rewards will be randomly selected from here utilizing a weight system.
entries:
  - displayName: 1x Diamond
    command: give %p% diamond 1
    weight: 1.0
  - displayName: 1x Gold Ingot
    command: give %p% gold_ingot 1
    weight: 1.0
```


---
#### Supervisor:

This plugin uses [cjcameron92/supervisor](https://github.com/cjcameron92/supervisor)'s [solo-dev](https://github.com/cjcameron92/supervisor/tree/dev-solo) branch as of `Sep-8-2024`, by the time you're reading this, that branch, or at least the current changes on it, would have already been merged into main version.

Supervisor is inspired by Spring Boot, the Framework is designed to be both expandable and modular, making it the perfect choice for developing robust Minecraft Spigot plugins.

From my personal experience... which I guess is bias being one of it's developer, it really makes life that much easier. Allowing the creation of this plugin in only 521 lines of code (ignoring white spaces)

Of course other utilities were used like [Aikar](https://github.com/aikar)'s [Annotation Command Framework](https://github.com/aikar/commands), ACF for short and [Lombok](https://projectlombok.org/).



echo "# TimedRewards" >> README.md
git add README.md
git commit -m "first commit"
git branch -M main
git push -u origin main
