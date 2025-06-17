
import { Outlet } from "react-router-dom";
import { ThemeProvider } from "@/components/theme-provider";

const AuthLayout = () => {
  return (
    <ThemeProvider attribute="class" defaultTheme="dark" enableSystem>
      <div className="flex min-h-screen w-full items-center justify-center bg-background p-4">
          <Outlet />
      </div>
    </ThemeProvider>
  );
};

export default AuthLayout;
