package cz.fencl.minicrm.application.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.regions.Region;

@NoArgsConstructor(access = AccessLevel.NONE)
public class Constants {
	
	public static final Region REGION = Region.EU_CENTRAL_1;
}
