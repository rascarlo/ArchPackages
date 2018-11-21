# Arch packages
*Arch Linux packages browser.*

The application makes use of the official API to browse the Arch Linux packages repos.

The package layout provides all available details:
- Description.
- Filename.
- Version.
- Arch.
- Repo.
- Groups.
- Upstream URL.
- License.
- Maintainers.
- Packager.
- Package size.
- Installed size.
- Build date.
- Last updated.
- Flag date.
- Dependencies.
- Make dependencies.
- Check dependencies.
- Optional dependencies.
- Conflicts.
- Provides.
- Replaces.
- Files.

Supported repos:
- core.
- extra.
- testing.
- multilib.
- multilib-testing.
- community
- community-testing.

Supported architecures: 
- any.
- x86_64.

Supported flags:
- All.
- Flagged.
- Not Flagged.

It supports the same query parameters as the HTML search form, except for Maintainer (todo), Packager (todo) and sort (not exposed by the API).

I use Arch BTW.


___
### Permissions
- **android.permission.INTERNET:** pull json (https://developer.android.com/reference/android/Manifest.permission.html#INTERNET)

___
### Links
- **API wiki:** https://wiki.archlinux.org/index.php/Official_repositories_web_interface
- **Arch Linux:** https://www.archlinux.org/
- **Arch Linux packages:** https://www.archlinux.org/packages/
