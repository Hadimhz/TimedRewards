# TimedRewards
##### The ultimate timed rewards plugin with no clutter.
---

This plugin gives you the ability to create unlimited TimeRewards, providing no UI the plugin registers a bunch of placeholders that give you full control over it, utilizing plugins like [DeluxeMenus](https://www.spigotmc.org/resources/deluxemenus.11734/) 

You can download this plugin off of spigot [here](https://www.spigotmc.org/resources/timed-rewards-%E2%9C%A8player-rewards.119467/)!

#### Why no interface? I sense you ask
Well, the reason is realistically quite simple, making a widely configurable interface is no easy task, and even then it would not give you the ability to customize it to your liking.
That's why it's fully up to you to create whatever interface you wish to create.
I mean... Look what you can create using simple DeluxMenus configs.

![08_01_52+630_08_09_2024_javaw](https://github.com/user-attachments/assets/5b9f9e9b-a261-4db8-9f23-8e1bddb23c8f)
![08_02_02+481_08_09_2024_javaw](https://github.com/user-attachments/assets/17bd4577-f21a-43b5-b69a-d5a3da3193ef)

You can get this file from [here](https://github.com/Hadimhz/TimedRewards/blob/master/rewards.yml).

If you liked this project, i wouldn't mind you starring it 🥺

#### Default Config:
```yaml
# This is sent to the client when a reward is claimed.
rewardMessage: 'You have received %reward% reward from %name%' 

# This is the message sent to the player when they're on cooldown.
cooldownMessage: 'This rewards is still on cooldown for %remaining%'

# This is the message sent to the player when they don't have the permission 
# needed to claim a reward.
noPermissionMessage: 'You do not have the permission required to claim this reward.'
```


#### Timed Reward Config:

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

#### Placeholders:

*Tested on the default reward config "daily"*

Placeholder | Description | Expected Result
-- | -- | -- 
%timedrewards_display:daily%  | Displays the Display name of the pool. | `Daily Reward`
%timedrewards_cooldown:daily% | Displays that player's long cooldown to this pool. | `5 hours, 3 minutes and 50 seconds`
%timedrewards_cooldown_short:daily% | Displays that player's short cooldown to this pool. | `5 hours`
%timedrewards_duration:daily% | Displays the cooldown duration for this pool | `24 hours`
%timedrewards_duration_short:daily% | Displays the cooldown duration for this pool | `24 hours`
%timedrewards_available:daily% | Displays if the player can claim this reward *(ignores permission)* | `true/false`


#### Commands:

Commands | Description | Permission
-- | -- | -- 
`/timerewards help` | Shows all the commands | None
`/timerewards claim <rewardPool>` | Claims the reward specified for the player. | `timerewards.claim`
`/timerewards list` | Lists all the current Timed Rewards. | `timerewards.admin`
`/timerewards claimFor <player> <rewardPool>` | Claims a reward for the player specified, this command will ignore permissions but it wouldn't ignore cooldown. | `timerewards.admin`
`/timerewards resetCooldown <player> <rewardPool>` | Resets the cooldown for the specified player on the specified reward pool. | `timerewards.admin`

---
### Supervisor:

This plugin uses [cjcameron92/supervisor](https://github.com/cjcameron92/supervisor)'s [solo-dev](https://github.com/cjcameron92/supervisor/tree/dev-solo) branch as of `Sep-8-2024`, by the time you're reading this, that branch, or at least the current changes on it, would have already been merged into main version.

Supervisor is inspired by Spring Boot, the Framework is designed to be both expandable and modular, making it the perfect choice for developing robust Minecraft Spigot plugins.

From my personal experience... which I guess is bias being one of it's developer, it really makes life that much easier. Allowing the creation of this plugin in only 521 lines of code (ignoring white spaces)

Of course other utilities were used like [Aikar](https://github.com/aikar)'s [Annotation Command Framework](https://github.com/aikar/commands), ACF for short and [Lombok](https://projectlombok.org/).
