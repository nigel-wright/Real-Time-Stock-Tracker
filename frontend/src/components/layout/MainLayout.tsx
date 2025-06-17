
import Sidebar from "./Sidebar";
import Header from "./Header";
import { Outlet } from "react-router-dom";
import { ThemeProvider } from "@/components/theme-provider";

const MainLayout = () => {
  return (
    <ThemeProvider attribute="class" defaultTheme="dark" enableSystem>
      <div className="flex min-h-screen w-full bg-background">
        <Sidebar />
        <div className="flex flex-1 flex-col">
          <main className="flex-1 p-4 md:p-6 flex flex-col">
            <Header />
            <div className="flex-1 mt-4">
              <Outlet />
            </div>
          </main>
        </div>
      </div>
    </ThemeProvider>
  );
};
export default MainLayout;
