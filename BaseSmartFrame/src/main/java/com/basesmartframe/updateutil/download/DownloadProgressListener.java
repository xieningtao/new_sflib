package com.basesmartframe.updateutil.download;

public interface DownloadProgressListener {
	void onDownloadSize(long size);

	void onErrorListener(String exception);

	void onDownloadFinish();


	void onStartDownload(long size);
}
