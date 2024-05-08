file_path = 'AnimatedDrawings/examples/bvh/fair1/hi.bvh'

with open(file_path, 'r') as file:
    lines = file.readlines()

result_lines = lines[10:]

with open(file_path, 'w') as file:
    file.writelines(result_lines)

print("-----result-----")
print(len(result_lines))
