package javaTester;

public class Topic_01_And_Or {

	public static void main(String[] args) {
		// Có 2 điều kiện
		// Kết hợp and hoặc or giữa 2 điều kiện này
		// Ra kết quả (True/ False)
		boolean firstCondition;
		boolean secondCondition;

		// AND: Nếu 1 trong 2 điều kiện mà sai = sai
		// Chỉ khi nào cả 2 đều đúng = đúng
		// ĐK 1 = TRUE FALSE FALSE TRUE
		// ĐK 2 = FALSE TRUE FALSE TRUE
		// Result = FALSE FALSE FALSE TRUE
		firstCondition = true;
		secondCondition = false;
		System.out.println(firstCondition && secondCondition);

		firstCondition = false;
		secondCondition = true;
		System.out.println(firstCondition && secondCondition);

		firstCondition = false;
		secondCondition = false;
		System.out.println(firstCondition && secondCondition);

		firstCondition = true;
		secondCondition = true;
		System.out.println(firstCondition && secondCondition);

		// OR: Nếu 1 trong 2 điều kiện mà đúng = đúng
		// ĐK 1 = TRUE FALSE FALSE TRUE
		// ĐK 2 = FALSE TRUE FALSE TRUE
		// Result = TRUE TRUE FALSE TRUE

		firstCondition = true;
		secondCondition = false;
		System.out.println(firstCondition || secondCondition);

		firstCondition = false;
		secondCondition = true;
		System.out.println(firstCondition || secondCondition);

		firstCondition = false;
		secondCondition = false;
		System.out.println(firstCondition || secondCondition);

		firstCondition = true;
		secondCondition = true;
		System.out.println(firstCondition || secondCondition);
	}

}
