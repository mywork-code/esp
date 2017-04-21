package com.apass.esp.domain.enums;

public class AwardActivity {
	// 活动有效性
	public enum ACTIVITY_STATUS {
		EFFECTIVE(1, "有效"), UNEFFECTIVE(0, "无效");
		ACTIVITY_STATUS(int code, String message) {
			this.code = code;
			this.message = message;
		}

		private int code;
		private String message;

		public int getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}

	}

	// 活动类型
	public enum ACTIVITY_TYPE {
		PERSONAL(0, "个人"), ORGANIZATION(1, "组织");
		ACTIVITY_TYPE(int code, String message) {
			this.code = code;
			this.message = message;
		}

		private int code;
		private String message;

		public int getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}

	}

	// 金额类型
	public enum AWARD_TYPE {
		GAIN(0, "获得"), WITHDRAW(1, "提现");
		AWARD_TYPE(int code, String message) {
			this.code = code;
			this.message = message;
		}

		private int code;
		private String message;

		public int getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}
	}

	// 处理金额状态
	public enum AWARD_STATUS {
		SUCCESS(0, "成功"), FAIL(1, "失败"), PROCESSING(2, "处理中");
		AWARD_STATUS(int code, String message) {
			this.code = code;
			this.message = message;
		}

		private int code;
		private String message;

		public int getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}
	}
}
