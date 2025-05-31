import "./App.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import ScrollToTop from "./utils/ScrollToTop";
import Layout from "./Layout";
import MainPage from "./pages/Main/Main";
import Faculty from "./pages/Menu/Faculty";
import GreetingPage from "./pages/Greeting/Greeting";
import ProfessorPage from "./pages/Professor/Professor";
import AssistantProfessorPage from "./pages/Professor/AssistantProfessor";
import StudentCouncilPage from "./pages/StudentCouncil/StudentCouncil";
import ContactPage from "./pages/Contact/Contact";
import Course from "./pages/Menu/Course";
import IntroPage from "./pages/Intro/Intro";
import ConvergenceMajorPage from "./pages/ConvergenceMd/ConvergenceMd";
import GraduationPage from "./pages/Graduation/Graduation";
import NewsPage from "./pages/News/News";
import NewsDetailPage from "./pages/News/NewsDetail";
import { ResponsiveProvider } from "./hooks/ResponsiveContext";
import MOUPage from "./pages/MOU/MOU";
import LabPage from "./pages/Lab/Lab";
import GraduatePage from "./pages/Graduate/Graduate";
import UndergraduatePage from "./pages/Undergraduate/Undergraduate";
import Research from "./pages/Menu/Research";
import CurriculumPage from "./pages/Curriculum/Curriculum";
import AuthRoute from "./utils/AuthRoute";
import NewsForm from "./pages/Admin/News/NewsForm";

const App = () => {
  return (
    <BrowserRouter>
      <ResponsiveProvider>
        <ScrollToTop />
        {/* <Routes>
        <Route path="/*" element={<MainPage />} />
      </Routes> */}
        <Layout>
          <Routes>
            {/* 메인 */}
            <Route path="/*" element={<MainPage />} />
            {/* 학부 소개 */}
            <Route path="/faculty" element={<Faculty />}>
              <Route path="greetings" element={<GreetingPage />} />
              <Route path="professor" element={<ProfessorPage />} />
              <Route
                path="assistant-professor"
                element={<AssistantProfessorPage />}
              />
              <Route path="student-council" element={<StudentCouncilPage />} />
              <Route path="contact" element={<ContactPage />} />
            </Route>
            {/* 학부 과정 */}
            <Route path="/undergraduate" element={<UndergraduatePage />}>
              <Route path="intro" element={<IntroPage />} />
              <Route path="curriculum" element={<CurriculumPage />} />
              <Route path="convergence-md" element={<ConvergenceMajorPage />} />
              <Route path="graduation" element={<GraduationPage />} />
            </Route>
            {/* 대학원 과정 */}
            <Route path="/graduate" element={<GraduatePage />} />
            {/* 교육/연구 */}
            <Route path="/research" element={<Research />}>
              <Route path="lab" element={<LabPage />} />
              <Route path="mou" element={<MOUPage />} />
            </Route>
            {/* 학부 소식 */}
            <Route path="/news" element={<NewsPage />} />
            <Route path="/news/:id" element={<NewsDetailPage />} />
            {/* 담당자 접근 가능<Admin> */}
            {/* <Route
              path="/news"
              element={
                <AuthRoute
                  element={<NewsForm />}
                  allowedRoles={[2, 3]}
                />
              }
            /> */}
            <Route path="/admin/news/form" element={<NewsForm />} />
          </Routes>
        </Layout>
      </ResponsiveProvider>
    </BrowserRouter>
  );
};

export default App;
