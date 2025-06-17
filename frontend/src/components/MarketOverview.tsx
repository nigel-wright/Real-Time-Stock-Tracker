
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { marketOverviewData } from "@/lib/mock-data";
import { cn } from "@/lib/utils";
import { TrendingDown, TrendingUp } from "lucide-react";

const MarketOverview = () => {
  return (
    <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4 mb-4">
      {marketOverviewData.map((item) => (
        <Card key={item.name}>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">{item.name}</CardTitle>
            {item.changeType === 'positive' ? (
              <TrendingUp className="h-4 w-4 text-green-500" />
            ) : (
              <TrendingDown className="h-4 w-4 text-red-500" />
            )}
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{item.value}</div>
            <p
              className={cn(
                "text-xs text-muted-foreground",
                item.changeType === 'positive' ? "text-green-500" : "text-red-500"
              )}
            >
              {item.change} from last day
            </p>
          </CardContent>
        </Card>
      ))}
    </div>
  );
};

export default MarketOverview;
