package com.basesmartframe.updateutil.download;

public interface DownloadProgressListener {
	public void onDownloadSize(long size);

	public void onErrorListener(String exception);

	public void onDownloadFinish();


	public void onStartDownload(long size);
}
