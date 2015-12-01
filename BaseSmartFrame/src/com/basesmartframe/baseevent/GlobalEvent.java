package com.basesmartframe.baseevent;

public class GlobalEvent {

	public static class NetworkEvent {
		public final boolean hasNetwork;
		public final String networkName;

		public NetworkEvent(boolean hasNetwork, String name) {
			this.hasNetwork = hasNetwork;
			this.networkName = name;
		}

	}
}
