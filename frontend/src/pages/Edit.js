import { useContext, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { DiaryStateContext } from "../App";
import DiaryEditor from "../components/DiaryEditor";

type Props = {
  id: number;
  emotion: number;
  content: string;
  date: number;
};
const Edit = () => {
  const useDiaryState = () => {
    const state = useContext(DiaryStateContext);
    if (!state) throw new Error("DiaryStateContext Not Found");
    return state;
  };
  const [originData, setOriginData] = useState<Props | undefined>();
  const navigate = useNavigate();
  const { id } = useParams() as { id: string };
  const diaryList = useDiaryState();

  useEffect(() => {
    const titleElem = document.getElementsByTagName("title")[0];
    titleElem.innerHTML = `감성 일기장 - ${id}번 일기 수정`;
  }, []);

  useEffect(() => {
    if (diaryList.length >= 1) {
      const targetDiary = diaryList.find((it) => it.id === parseInt(id));

      if (targetDiary) {
        setOriginData(targetDiary);
      } else {
        navigate("/", { replace: true });
      }
    }
  }, [diaryList, id]);

  return (
    <div>
      {originData && <DiaryEditor isEdit={true} originData={originData} />}
    </div>
  );
};

export default Edit;
