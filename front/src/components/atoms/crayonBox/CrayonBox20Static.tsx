import "./CrayonBox20.scss";

interface Props {
  children: React.ReactNode;
}

function CrayonBox20Static({ children }: Props) {
  return <section className="BoxContainer">{children}</section>;
}

export default CrayonBox20Static;
