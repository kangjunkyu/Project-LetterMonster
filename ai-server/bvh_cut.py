file_path = 'AnimatedDrawings/examples/bvh/rokoko/slip.bvh'

with open(file_path, 'r') as file:
    lines = file.readlines()

result_lines = lines[:-20]

with open(file_path, 'w') as file:
    file.writelines(result_lines)

print("-----result-----")
print(len(result_lines))
