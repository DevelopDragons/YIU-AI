import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
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
import SignUpPage from "./pages/SignUp/SignUp";
import SignInPage from "./pages/SignIn/SignIn";
import AdminPage from "./pages/Admin";
import { UserRole } from "./models/enum";
import NewsList from "./pages/Admin/News/NewsList";
import AdminHome from "./pages/Admin/Home";

const App = () => {
  return (
    <BrowserRouter>
      <ResponsiveProvider>
        <ScrollToTop />
        <Routes>
          {/* ---------------- Admin 페이지 ---------------- */}
          <Route
            path="/admin/*"
            element={
              <AuthRoute
                element={<AdminPage />}
                allowedRoles={[UserRole.ADMIN, UserRole.SUPER]}
              />
            }
          >
            <Route index element={<AdminHome />} />
            <Route path="news" element={<NewsList />} />
            <Route path="news/form" element={<NewsForm />} />
          </Route>

          {/* ---------------- 일반 페이지 (Layout 포함) ---------------- */}
          <Route element={<Layout />}>
            <Route path="/*" element={<MainPage />} />
            <Route path="/sign-up" element={<SignUpPage />} />
            <Route path="/sign-in" element={<SignInPage />} />

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
          </Route>
        </Routes>
      </ResponsiveProvider>
    </BrowserRouter>
  );
};

export default App;
