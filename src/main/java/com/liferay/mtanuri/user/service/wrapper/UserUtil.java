package com.liferay.mtanuri.user.service.wrapper;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UserUtil {

	public static String getUniqueEmail(long companyId, String emailAddress) {

		if (_log.isDebugEnabled()) {
			_log.debug("Searching duplicates for " + emailAddress);
		}

		String originalEmail = emailAddress;
		boolean isUnique = getUserIdByEmailAddressWrapper(companyId, emailAddress) == 0;
		int emailHitsCounter = 1;

		while (!isUnique) {
			emailAddress = originalEmail.replace("@", emailHitsCounter + "@");
			if (_log.isDebugEnabled()) {
				_log.debug("Searching duplicates for " + emailAddress);
			}
			isUnique = getUserIdByEmailAddressWrapper(companyId, emailAddress) == 0;
			emailHitsCounter++;
		}
		return emailAddress;
	}

	public static String getUniqueEmail(long companyId, String emailAddress, Long userId) {
		if (userId != null) {
			if (getUserIdByEmailAddressWrapper(companyId, emailAddress) == userId) {
				return emailAddress;
			}
		}
		return getUniqueEmail(companyId, emailAddress);
	}

	private static long getUserIdByEmailAddressWrapper(long companyId, String emailAddress) {
		long userIdByEmailAddress = 0L;
		try {
			userIdByEmailAddress = UserLocalServiceUtil.getUserIdByEmailAddress(companyId, emailAddress);
		} catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("No user with emailAdress '" + emailAddress + "' has been found!");
			}
		}
		return userIdByEmailAddress;
	}

	public static ServiceContext setEmailIntoCustomField(String emailAddress, ServiceContext serviceContext) {
		Map<String, Serializable> expandoBridgeAttributes = new HashMap<String, Serializable>();
		expandoBridgeAttributes.put("repeatable-email", emailAddress);
		serviceContext.setExpandoBridgeAttributes(expandoBridgeAttributes);
		return serviceContext;
	}

	private static final Log _log = LogFactoryUtil.getLog(UserUtil.class);

}
