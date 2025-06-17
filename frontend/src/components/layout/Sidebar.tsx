
import { NavLink } from "react-router-dom";
import { LayoutDashboard, Cog, TrendingUp } from "lucide-react";
import { Tooltip, TooltipContent, TooltipTrigger } from "@/components/ui/tooltip";

const Sidebar = () => {
  return (
    <aside className="hidden w-16 flex-col border-r bg-card sm:flex">
      <nav className="flex flex-col items-center gap-4 px-2 py-4">
        <NavLink
          to="/dashboard"
          className="group flex h-9 w-9 shrink-0 items-center justify-center gap-2 rounded-full bg-primary text-lg font-semibold text-primary-foreground md:h-8 md:w-8 md:text-base"
        >
          <TrendingUp className="h-4 w-4 transition-all group-hover:scale-110" />
          <span className="sr-only">StockPulse</span>
        </NavLink>
        <Tooltip>
          <TooltipTrigger asChild>
            <NavLink
              to="/dashboard"
              className={({ isActive }) =>
                `flex h-9 w-9 items-center justify-center rounded-lg transition-colors hover:text-foreground md:h-8 md:w-8 ${
                  isActive ? "bg-accent text-accent-foreground" : "text-muted-foreground"
                }`
              }
            >
              <LayoutDashboard className="h-5 w-5" />
              <span className="sr-only">Dashboard</span>
            </NavLink>
          </TooltipTrigger>
          <TooltipContent side="right">Dashboard</TooltipContent>
        </Tooltip>
      </nav>
      <nav className="mt-auto flex flex-col items-center gap-4 px-2 py-4">
        <Tooltip>
          <TooltipTrigger asChild>
            <NavLink
              to="/settings"
              className={({ isActive }) =>
                `flex h-9 w-9 items-center justify-center rounded-lg transition-colors hover:text-foreground md:h-8 md:w-8 ${
                  isActive ? "bg-accent text-accent-foreground" : "text-muted-foreground"
                }`
              }
            >
              <Cog className="h-5 w-5" />
              <span className="sr-only">Settings</span>
            </NavLink>
          </TooltipTrigger>
          <TooltipContent side="right">Settings</TooltipContent>
        </Tooltip>
      </nav>
    </aside>
  );
};

export default Sidebar;
