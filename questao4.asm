.text
	addi $t1, $t1, 21474
	addi $t2, $t2, -2147100000
	addu $t3, $t2, $t2
	li $t1, 0
	li $t2, 0
	li $t3, 0
	addi $t1, $t1, 21474
	addi $t2, $t2, -21471
	addu $t3, $t2, $t2
	mult $t1, $t2
	mfhi $t4
	mflo $t5
	multu $t1, $t2
	mfhi $t4
	mflo $t5
	
	div $t1, $t2
	mfhi $t4
	mflo $t5
	divu $t1, $t2
	mfhi $t4
	mflo $t5
