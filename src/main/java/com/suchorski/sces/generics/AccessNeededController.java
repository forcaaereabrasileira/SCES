package com.suchorski.sces.generics;

import com.github.adminfaces.template.exception.AccessDeniedException;
import com.suchorski.sces.oauth.ResourceUserData;

public interface AccessNeededController {
	
	public default void checkAccess(ResourceUserData usuario, String roles) throws AccessDeniedException {
		if (!usuario.hasPerfil(roles)) {
			throw new AccessDeniedException();
		}
	}
	
	public void grantAccess() throws AccessDeniedException;

}
