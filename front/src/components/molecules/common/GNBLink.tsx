import styles from "./GNB.module.scss";
import { Link } from "react-router-dom";
import { useTranslation } from "react-i18next";

interface Props {
  to: string;
  iconSrc: string;
  altText: string;
  translationKey: string;
}

const GNBLink = ({ to, iconSrc, altText, translationKey }: Props) => {
  const { t } = useTranslation();

  return (
    <Link className={styles.GNBbutton} to={to}>
      <img src={iconSrc} alt={altText} />
      <h5>{t(translationKey)}</h5>
    </Link>
  );
};

export default GNBLink;
