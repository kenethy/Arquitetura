addi $1, $0, 511
addi $3, $0, 1
addi $4, $0, 8192
sw $1, 0($4)
lb $2, 0($4)
addi $2, $2, 3
sb $2, 2($4)
lbu $2, 0($4)
addi $2, $2, -1
sb $2, 3($4)
bltz $2, label0
beq $2, $3, label0
bne $2, $0, label0
addi $2, $2, 1
label0: jal label1
j label2
nop
label1: addi $3, $3, -1
jr $31
label2: lw $5, 0($4)

