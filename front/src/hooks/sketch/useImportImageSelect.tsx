import { useCallback, useState } from "react";
import { SIZE } from "../../components/pages/sketch/Paint";

function useImportImageSelect() {
  const [image, setImage] = useState<HTMLImageElement | undefined>();

  const onImportImageSelect: React.ChangeEventHandler<HTMLInputElement> =
    useCallback((e) => {
      if (e.target.files?.[0]) {
        const imageUrl = URL.createObjectURL(e.target.files[0]);
        const imageImport = new Image(SIZE / 2, SIZE / 2);
        imageImport.src = imageUrl;
        setImage(imageImport);
      }
      e.target.value = "";
    }, []);

  return { image, setImage, onImportImageSelect };
}

export default useImportImageSelect;
