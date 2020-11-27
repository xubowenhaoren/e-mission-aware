# e-mission-aware-battery
#### What's this?

This project is a plugin for e-mission platform that incorporates AWARE data collection functionalities. 

To learn more about the e-mission platform, click [here](https://github.com/e-mission/e-mission-phone/).

To learn more about the AWARE platform, click [here](http://awareframework.com/). 



#### TL;DR. How do I add this plugin? 

1. Fork my project and/or pull it to your local workspace. 

2. Notice that in `platforms/android/app/build-extras.gradle`, there is a hardcoded `applicationId`. Change it to your application name.

   ```gradle
   applicationId "edu.berkeley.eecs.emission"
   ```

3. For this [line](https://github.com/xubowenhaoren/e-mission-aware-battery/blob/master/src/android/AwarePlugin.java#L211), change it to your AWARE study link. Continue reading the "How do I create an AWARE study from scratch?" section if you don't know what this is.

4. In your local emission workspace terminal, type the following commands

   ```bash
   npx cordova plugin add file:/Users/bowenxu2/Documents/GitHub/e-mission-aware-battery/
   ```

   Note that `/Users/bowenxu2/Documents/GitHub/e-mission-aware-battery/` is my local folder. You need to change it to your local folder.

5. Design your own UI for the plugin, or copy and use my UI in my e-mission-phone fork. For a complete list of modifications necessary to import my UI, view this [diff page](https://github.com/e-mission/e-mission-phone/compare/master...xubowenhaoren:aware). 



#### How do I create an e-mission study and get my workspace from scratch? 

1. Follow the [installation guide](https://github.com/e-mission/e-mission-server#installation) to deploy the e-mission server on the cloud service provider of your choice. 
2. Follow the [setup guide](https://github.com/e-mission/e-mission-phone/#installing-one-time-only) to deploy your copy of the e-mission client.
   - If you need further customizations, check [this guide](https://github.com/e-mission/e-mission-docs/blob/master/docs/dev/front/create_a_new_custom_client.md) in the e-mission-docs.
3. If you encounter any issues regarding any of these steps, check the existing [e-mission-docs](https://github.com/e-mission/e-mission-docs) as well as the [issues page](https://github.com/e-mission/e-mission-docs/issues?q=is%3Aissue). 



#### How do I create an AWARE study from scratch? 

1. The complete [setup guide](https://awareframework.com/hosting-your-own-aware-dashboard/) can be found on the AWARE framework website. 
2. Follow [this guide](https://awareframework.com/run-a-study-with-aware/) to create your AWARE study, change data collection sampling intervals, and get the study link to be used in AWARE clients. 

