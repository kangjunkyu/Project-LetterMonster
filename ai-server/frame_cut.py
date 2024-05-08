file_path = 'AnimatedDrawings/examples/bvh/fair1/hi.bvh'

with open(file_path, 'r') as file:
    lines = file.readlines()

result_lines = []

for i in range(len(lines)):
    if (i % 2) == 0:
        result_lines.append(lines[i])

with open(file_path, 'w') as file:
    file.writelines(result_lines)

print("-----result-----")
print(len(result_lines))



