package com.starbugs.Core.Config;

public final class StarBugsRoles {
	/*
	 * ROLES:
	 * 1- SB_ROOT_X001: Root user for starbugs adminstration -> Can Do everything in the system
	 * 2- SB_ADMIN_X002: Admin User for starbugs-services - > Like root user but cannot override subscription details or delete root user
	 * 3- SB_CLIENT_ROOT_X003: Root user for client -> Can Do anything related to the client subscription
	 * 4- SB_CLIENT_ADMIN_X004: admin user for client -> Like admin but without manipulating subscriptions or root user
	 * 5- SB_CLIENT_EMPLOYEE_X005: Employee Role
	 * */
	
	private static final String[] StarBugsRootUserRoles = {"SB_ROOT_X001", "SB_ADMIN_X002", "SB_CLIENT_ROOT_X003", "SB_CLIENT_ADMIN_X004", "SB_CLIENT_EMPLOYEE_X005" };
	private static final String[] StarBugsAdminUserRoles = {"SB_ADMIN_X002", "SB_CLIENT_ROOT_X003", "SB_CLIENT_ADMIN_X004", "SB_CLIENT_EMPLOYEE_X005"};
	private static final String[] StarBugsClientRootUserRoles = {"SB_CLIENT_ROOT_X003", "SB_CLIENT_ADMIN_X004", "SB_CLIENT_EMPLOYEE_X005"};
	private static final String[] StarBugsClientAdminUserRoles = {"SB_CLIENT_ADMIN_X004", "SB_CLIENT_EMPLOYEE_X005"};
	private static final String[] StarBugsEmployeeRoles = {"SB_CLIENT_EMPLOYEE_X005"};
	
	private StarBugsRoles() {
	}

	public static String[] getStarBugsRootUserRoles() {
		return StarBugsRootUserRoles;
	}
	
	public static String[] getStarBugsAdminUserRoles() {
		return StarBugsAdminUserRoles;
	}
	
	public static String[] getStarBugsClientRootUserRoles() {
		return StarBugsClientRootUserRoles;
	}
	
	public static String[] getStarBugsClientAdminUserRoles() {
		return StarBugsClientAdminUserRoles;
	}
	
	public static String[] getStarBugsEmployeeRoles() {
		return StarBugsEmployeeRoles;
	}
	
}
