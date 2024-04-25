function formatMotionName(name: string) {
  const cleanedName = name.replace(/^\d+_/, "");
  return cleanedName.replace(/_/g, " ").toUpperCase();
}

export default formatMotionName;
