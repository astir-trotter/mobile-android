/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 12/2/16
 */

package com.astir_trotter.atcustom.ui.activity.base;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import com.astir_trotter.atcustom.ui.activity.BaseActivity;
import com.astir_trotter.atcustom.ui.activity.CrashReporterActivity;
import com.astir_trotter.atcustom.util.LogHelper;
import com.astir_trotter.atcustom.util.TimeUtils;
import com.astir_trotter.atcustom.util.ViewUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;

public class AutoCrashReporter implements Thread.UncaughtExceptionHandler {
	private static final String TAG = AutoCrashReporter.class.getSimpleName();

	private static String DEFAULT_EMAIL_SUBJECT = "New Crash Report Generated";

	private String[] recipients;
	private boolean startAttempted = false;

	private String versionName;
	//private String buildNumber;
	private String packageName;
	private String filePath;
	private String phoneModel;
	private String androidVersion;
	private String board;
	private String brand;
	private String device;
	private String display;
	private String fingerPrint;
	private String host;
	private String id;
	private String manufacturer;
	private String model;
	private String product;
	private String tags;
	private long time;
	private String type;
	private String user;
	private HashMap<String, String> customParameters = new HashMap<String, String>();

//	private Thread.UncaughtExceptionHandler previousHandler;
	private static AutoCrashReporter sInstance;
	private Application application;

	private AutoCrashReporter(Application application){
		this.application = application;
	}

	public static AutoCrashReporter get(Application application) {
		if (sInstance == null)
			sInstance = new AutoCrashReporter(application);
		return sInstance;
	}

	public void start() {
		if(startAttempted) {
			LogHelper.log(TAG, "Already started");
			return;
		}
//		previousHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);

		startAttempted = true;
	}

	/**
	 * (Required) Defines one or more email addresses to send bug reports to. This method MUST be
	 * called before calling start This method CANNOT be called after calling
	 * start.
	 *
	 * @param emailAddresses one or more email addresses
	 * @return the current AutoCrashReporter instance (to allow for method chaining)
	 */

	public AutoCrashReporter setEmailAddresses(final String... emailAddresses) {
		if (startAttempted) {
			throw new IllegalStateException(
					"EmailAddresses must be set before start");
		}
		this.recipients = emailAddresses;
		return this;
	}

	/**
	 * (Optional) Defines a custom subject line to use for all bug reports. By default, reports will
	 * use the string defined in DEFAULT_EMAIL_SUBJECT This method CANNOT be called
	 * after calling start.
	 * @param emailSubject custom email subject line
	 * @return the current AutoCrashReporter instance (to allow for method chaining)
	 */
	public AutoCrashReporter setEmailSubject(final String emailSubject) {
		if (startAttempted) {
			throw new IllegalStateException("EmailSubject must be set before start");
		}

		DEFAULT_EMAIL_SUBJECT = emailSubject;
		return this;
	}

	public void addCustomData(String Key, String Value) {
		customParameters.put(Key, Value);
	}

	private String createCustomInfoString() {
		String customInfo = "";
		for (Object currentKey : customParameters.keySet()) {
			String currentVal = customParameters.get(currentKey);
			customInfo += currentKey + " = " + currentVal + "\n";
		}
		return customInfo;
	}

	private long getAvailableInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return (availableBlocks * blockSize)/(1024*1024);
	}

	private long getTotalInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		return (totalBlocks * blockSize)/(1024*1024);
	}

	private void recordInformations(Context context) {
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi;
			// Version
			pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			//buildNumber = currentVersionNumber(context);
			// Package name
			packageName = pi.packageName;

			// Device model
			phoneModel = Build.MODEL;
			// Android version
			androidVersion = Build.VERSION.RELEASE;

			board = Build.BOARD;
			brand = Build.BRAND;
			device = Build.DEVICE;
			display = Build.DISPLAY;
			fingerPrint = Build.FINGERPRINT;
			host = Build.HOST;
			id = Build.ID;
			model = Build.MODEL;
			product = Build.PRODUCT;
			manufacturer = Build.MANUFACTURER;
			tags = Build.TAGS;
			time = Build.TIME;
			type = Build.TYPE;
			user = Build.USER;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String createInformationString() {
		recordInformations(application);
		StringBuilder infoStringBuffer = new StringBuilder();
		infoStringBuffer.append("\nVERSION		: ").append(versionName);
		infoStringBuffer.append("\nPACKAGE      : ").append(packageName);
		infoStringBuffer.append("\nFILE-PATH    : ").append(filePath);
		infoStringBuffer.append("\nPHONE-MODEL  : ").append(phoneModel);
		infoStringBuffer.append("\nANDROID_VERS : ").append(androidVersion);
		infoStringBuffer.append("\nBOARD        : ").append(board);
		infoStringBuffer.append("\nBRAND        : ").append(brand);
		infoStringBuffer.append("\nDEVICE       : ").append(device);
		infoStringBuffer.append("\nDISPLAY      : ").append(display);
		infoStringBuffer.append("\nFINGER-PRINT : ").append(fingerPrint);
		infoStringBuffer.append("\nHOST         : ").append(host);
		infoStringBuffer.append("\nID           : ").append(id);
		infoStringBuffer.append("\nMODEL        : ").append(model);
		infoStringBuffer.append("\nPRODUCT      : ").append(product);
		infoStringBuffer.append("\nMANUFACTURER : ").append(manufacturer);
		infoStringBuffer.append("\nTAGS         : ").append(tags);
		infoStringBuffer.append("\nTIME         : ").append(time);
		infoStringBuffer.append("\nTYPE         : ").append(type);
		infoStringBuffer.append("\nUSER         : ").append(user);
		infoStringBuffer.append("\nTOTAL-INTERNAL-MEMORY     : ").append(getTotalInternalMemorySize()+" mb");
		infoStringBuffer.append("\nAVAILABLE-INTERNAL-MEMORY : ").append(getAvailableInternalMemorySize()+" mb");

		return infoStringBuffer.toString();
	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		LogHelper.log(TAG, "====uncaughtException");

		StringBuilder reportStringBuffer = new StringBuilder();
		reportStringBuffer.append("Error Report collected on : ").append(new Date().toString());
		reportStringBuffer.append("\n\nInformations :\n==============");
		reportStringBuffer.append(createInformationString());
		String customInfo = createCustomInfoString();
		if(!customInfo.equals("")) {
			reportStringBuffer.append("\n\nCustom Informations :\n==============\n");
			reportStringBuffer.append(customInfo);
		}

		reportStringBuffer.append("\n\nStack :\n==============\n");
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		e.printStackTrace(printWriter);
		reportStringBuffer.append(result.toString());

		reportStringBuffer.append("\nCause :\n==============");
        // If the exception was thrown in a background thread inside
		// AsyncTask, then the actual exception can be found with getCause
		Throwable cause = e.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			reportStringBuffer.append(result.toString());
			cause = cause.getCause();
		}
		printWriter.close();
		reportStringBuffer.append("\n\n**** End of current Report ***");
		LogHelper.log(TAG, "Report: "+reportStringBuffer.toString());
		saveAsFile(reportStringBuffer.toString());

		Intent intent = new Intent(application, CrashReporterActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		application.startActivity(intent);

		//previousHandler.uncaughtException(t, e);

		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(10);
	}



	private void sendErrorMail(Context context, String errorContent) {
		LogHelper.log(TAG, "====sendErrorMail");
		Intent sendIntent = new Intent(Intent.ACTION_SEND);
		String subject = DEFAULT_EMAIL_SUBJECT;
		String body = "\n\n" + errorContent + "\n\n";
		sendIntent.putExtra(Intent.EXTRA_EMAIL, recipients);
		sendIntent.putExtra(Intent.EXTRA_TEXT, body);
		sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
		sendIntent.setType("message/rfc822");
		//sendIntent.setType("text/html");
		sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(Intent.createChooser(sendIntent, "Title:"));
	}

	private void saveAsFile(String errorContent) {
		LogHelper.log(TAG, "====SaveAsFile");
		try {
			String fileName = application.getPackageName() + "_" + TimeUtils.curTime() + ".stacktrace";
			FileOutputStream fos = application.openFileOutput(fileName, Context.MODE_PRIVATE);
			fos.write(errorContent.getBytes());
			fos.close();
		} catch (Exception e) {
			// ...
		}
	}

	private String[] getErrorFileList() {
		File dir = new File(filePath + "/");
		// Try to create the files folder if it doesn't exist
		dir.mkdir();
		// Filter for ".stacktrace" files
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".stacktrace");
			}
		};
		return dir.list(filter);
	}

	private boolean bIsThereAnyErrorFile() {
		return getErrorFileList().length > 0;
	}

	public void checkErrorAndSendMail(Context context) {
		try {
			filePath = context.getFilesDir().getAbsolutePath();
			if (bIsThereAnyErrorFile()) {
				StringBuilder wholeErrorTextSB = new StringBuilder();

				String[] errorFileList = getErrorFileList();
				int curIndex = 0;
				final int maxSendMail = 5;
				for (String curString : errorFileList) {
					if (curIndex++ <= maxSendMail) {
						wholeErrorTextSB.append("New Trace collected :\n=====================\n");
						String filePathStr = filePath + "/" + curString;
						BufferedReader input = new BufferedReader(
								new FileReader(filePathStr));
						String line;
						while ((line = input.readLine()) != null) {
							wholeErrorTextSB.append(line + "\n");
						}
						input.close();
					}

					// DELETE FILES !!!!
					File curFile = new File(filePath + "/" + curString);
					curFile.delete();
				}
				sendErrorMail(context, wholeErrorTextSB.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
