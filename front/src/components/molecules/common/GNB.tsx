import styles from "./GNB.module.scss";
import { Page_Url } from "../../../router/Page_Url";
import GNBLink from "./GNBLink";

// 아이콘
import sketchbook from "../../../assets/GNBIcon/sketchbook.svg";
import draw from "../../../assets/GNBIcon/draw.svg";
import letter from "../../../assets/GNBIcon/letter.svg";
import home from "../../../assets/GNBIcon/home.svg";

function GNB() {
  const links = [
    {
      to: Page_Url.Main,
      iconSrc: home,
      altText: "집으로",
      translationKey: "nav.home",
    },
    {
      to: Page_Url.Sketch,
      iconSrc: draw,
      altText: "그리기",
      translationKey: "nav.drawing",
    },
    {
      to: Page_Url.SketchbookList,
      iconSrc: sketchbook,
      altText: "스케치북",
      translationKey: "nav.sketchbook",
    },
    {
      to: Page_Url.WriteLetter,
      iconSrc: letter,
      altText: "편지 쓰기",
      translationKey: "nav.letter",
    },
  ];

  return (
    <header className={styles.header}>
      {links.map((link) => (
        <GNBLink key={link.to} {...link} />
      ))}
    </header>
  );
}

export default GNB;
