![​alt text](https://proxy.spigotmc.org/62fa18f5c4a5c3781945af2eb343e4ecd212e917?url=http%3A%2F%2Fi.imgur.com%2FgESvUXG.png "Logo")

# Overview
This is an advanced menu/gui which hooks into permission plugins to allow you to display all your staff members. The plugin is also open-source allowing for others to look at the code, and potentially submit changes to improve the plugin. The plugin source code can be found on [GitHub](https://github.com/tehneon/staffdisplay).

# Screenshots
![alt text](https://proxy.spigotmc.org/87b071977f9fc2da12ed54d37beda487481c0617?url=http%3A%2F%2Fi.imgur.com%2FfaftNgZ.png "pic")

# Commands and Permissions
With the config being super flexible you're able to change pretty much everything corresponding to this section, but this is what the defaults are set to.

# **/staffdisplay**

Permission: `staffdisplay.use`

Opens the menu​

# **/staffdisplay reload**

Permission: `staffdisplay.reload`

Reloads the config and force updates the players​


# Bugs and Issues
If you run into any kind of bugs or issues please do submit the issue/bug via GitHub as it will be the easiest way of seeing the issue.


# Configuration
The configuration file allows for a large amount of customization to the plugin. You are capable of changing the command, and aliases from /staffdisplay to whatever you'd like. You're capable of changing the permissions for the commands as well. The default permission node prefix is "staffdisplay." but you're able to change this as well.

You're able to allow certain ranks display differently with the items section under menu. To do this you simply just copy the default portion, shown as:

```
default:
   # The display name of the item
   display: "&c{username}"
   # The lore of the item
   lore:
    - "&6Staff Member"
    - "&7IGN: &e&l{username}"
    - "&7Rank: &e&l{rank}"
```

And paste it and rename default to the group which you want it to correspond to like so:

```
Admin:
   # The display name of the item
   display: "&cAdmin | {username}"
   # The lore of the item
   lore:
    - "&6This is a really cool admin!"
```

If done correctly the config should look like:

```
items:
default:
  # The display name of the item
  display: "&c{username}"
  # The lore of the item
  lore:
   - "&6Staff Member"
   - "&7IGN: &e&l{username}"
   - "&7Rank: &e&l{rank}"
Admin:
   # The display name of the item
   display: "&cAdmin | {username}"
   # The lore of the item
   lore:
    - "&6This is a really cool admin!"
```


# Other Information
Currently with StaffDisplay version 1.0, the only permissions plugin that is supported is PermissionsEx.

# PermissionsEx related:
The plugin is currently being built with PermissionsEx 1.23.2-SNAPSHOT, but it will work with a few of the previous versions such as 1.22.10 which is used for 1.7.10 servers
