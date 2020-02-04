# unique-email-service-wrapper
Hi! I'm a Liferay 7.x UserServiceWrapper. Whenever an user is added or updated I check whether its emailAddress is unique, if not I automatically update its emailAddress by adding a sequential number on it. 

The given emailAddress is moved to a custom field.

OBS: You need to create a User custom field > type: Text (not localized) > name: repeatable-email.

Nice to meet you!
