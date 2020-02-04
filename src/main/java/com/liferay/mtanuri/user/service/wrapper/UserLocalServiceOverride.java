package com.liferay.mtanuri.user.service.wrapper;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceWrapper;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author marceltanuri
 */
@Component(immediate = true, property = {}, service = ServiceWrapper.class)
public class UserLocalServiceOverride extends UserLocalServiceWrapper {

	public UserLocalServiceOverride() {
		super(null);
	}

	@Reference(unbind = "-")
	private void serviceSetter(UserLocalService userLocalService) {
		setWrappedService(userLocalService);
	}

	@Override
	public User updateUser(long userId, String oldPassword, String newPassword1, String newPassword2,
			boolean passwordReset, String reminderQueryQuestion, String reminderQueryAnswer, String screenName,
			String emailAddress, long facebookId, String openId, boolean hasPortrait, byte[] portraitBytes,
			String languageId, String timeZoneId, String greeting, String comments, String firstName, String middleName,
			String lastName, long prefixId, long suffixId, boolean male, int birthdayMonth, int birthdayDay,
			int birthdayYear, String smsSn, String facebookSn, String jabberSn, String skypeSn, String twitterSn,
			String jobTitle, long[] groupIds, long[] organizationIds, long[] roleIds,
			List<UserGroupRole> userGroupRoles, long[] userGroupIds, ServiceContext serviceContext)
			throws PortalException {

		if (_log.isDebugEnabled()) {
			_log.debug("Overriding UserLocalServiceOverride updateUser method");
		}

		User user = super.getUser(userId);

		String uniqueEmailAddress = UserUtil.getUniqueEmail(user.getCompanyId(), emailAddress, userId);

		UserUtil.setEmailIntoCustomField(emailAddress, serviceContext);

		return super.updateUser(userId, oldPassword, newPassword1, newPassword2, passwordReset, reminderQueryQuestion,
				reminderQueryAnswer, screenName, uniqueEmailAddress, facebookId, openId, hasPortrait, portraitBytes,
				languageId, timeZoneId, greeting, comments, firstName, middleName, lastName, prefixId, suffixId, male,
				birthdayMonth, birthdayDay, birthdayYear, smsSn, facebookSn, jabberSn, skypeSn, twitterSn, jobTitle,
				groupIds, organizationIds, roleIds, userGroupRoles, userGroupIds, serviceContext);
	}

	@Override
	public User updateUser(User user) {
		if (_log.isDebugEnabled()) {
			_log.debug("Overriding UserLocalServiceOverride updateUser(User user) method");
		}
		user.setEmailAddress(UserUtil.getUniqueEmail(user.getCompanyId(), user.getEmailAddress(), user.getUserId()));
		return super.updateUser(user);
	}

	private static final Log _log = LogFactoryUtil.getLog(UserLocalServiceOverride.class);

}