import styles from "./SketchbookListItem.module.scss";

interface Props {
  item: {
    id: string;
    isPublic: boolean;
    shareLink: string;
    name: string;
  };
}

function SketchbookListItem({ item }: Props) {
  return <div className={styles.sketchbookListItem}>{item.name}</div>;
}

export default SketchbookListItem;
