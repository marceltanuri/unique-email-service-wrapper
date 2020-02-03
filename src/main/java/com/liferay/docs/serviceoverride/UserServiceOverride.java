package com.liferay.docs.serviceoverride;

import com.liferay.announcements.kernel.model.AnnouncementsDelivery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.model.Website;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.service.UserServiceWrapper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author marceltanuri
 */
@Component(immediate = true, property = {}, service = ServiceWrapper.class)
public class UserServiceOverride extends UserServiceWrapper {

	public UserServiceOverride() {
		super(null);
	}

	@Reference(unbind = "-")
	private void serviceSetter(UserService userService) {
		setWrappedService(userService);
	}

	@Override
	public com.liferay.portal.kernel.model.User addUser(long companyId, boolean autoPassword, String password1,
			String password2, boolean autoScreenName, String screenName, String emailAddress, long facebookId,
			String openId, java.util.Locale locale, String firstName, String middleName, String lastName, long prefixId,
			long suffixId, boolean male, int birthdayMonth, int birthdayDay, int birthdayYear, String jobTitle,
			long[] groupIds, long[] organizationIds, long[] roleIds, long[] userGroupIds,
			java.util.List<Address> addresses, java.util.List<EmailAddress> emailAddresses,
			java.util.List<Phone> phones, java.util.List<Website> websites,
			java.util.List<AnnouncementsDelivery> announcementsDelivers, boolean sendEmail,
			ServiceContext serviceContext) throws PortalException {

		if (_log.isDebugEnabled()) {
			_log.debug("Overriding UserService addUser method");
		}

		emailAddress = UserUtil.getUniqueEmail(companyId, emailAddress);

		return super.addUser(companyId, autoPassword, password1, password2, autoScreenName, screenName, emailAddress,
				facebookId, openId, locale, firstName, middleName, lastName, prefixId, suffixId, male, birthdayMonth,
				birthdayDay, birthdayYear, jobTitle, groupIds, organizationIds, roleIds, userGroupIds, addresses,
				emailAddresses, phones, websites, announcementsDelivers, sendEmail, serviceContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(UserServiceOverride.class);
}