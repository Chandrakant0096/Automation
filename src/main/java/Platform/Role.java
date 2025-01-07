package Platform;

import java.util.Set;

import Platform.Role_Permissions.Permission;

public class Role
{
	
    private String name;
    private Set<Permission> permissions;

    public Role(String name, Set<Permission> permissions)
    {
        this.name = name;
        this.permissions = permissions;
    }

    public String getName() {
        return name;
    }

    public Set<Permission> getPermissions()
    {
        return permissions;
    }




}
