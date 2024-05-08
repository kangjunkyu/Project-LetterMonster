file_path = 'AnimatedDrawings/examples/bvh/fair1/hi.bvh'

with open(file_path, 'r') as file:
    lines = file.readlines()

result_lines = []

result_lines.extend(lines)

result_lines.extend(lines[::-1])

with open(file_path, 'w') as file:
    file.writelines(result_lines)

print(len(result_lines))
