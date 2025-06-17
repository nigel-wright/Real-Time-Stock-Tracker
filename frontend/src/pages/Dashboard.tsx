
import { useState } from "react";
import { ResizableHandle, ResizablePanel, ResizablePanelGroup } from "@/components/ui/resizable";
import StockChart from "@/components/StockChart";
import MarketOverview from "@/components/MarketOverview";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import ChartConfigurationPanel, { ChartConfig } from "@/components/ChartConfigurationPanel";
import { Button } from "@/components/ui/button";
import { Plus } from "lucide-react";

const initialCharts: ChartConfig[] = [
  { id: 'chart-1', name: 'Chart 1', symbol: 'AAPL', timeframe: '1D', indicators: ['RSI'], chartType: 'area', algorithm: 'None' },
  { id: 'chart-2', name: 'Chart 2', symbol: 'GOOGL', timeframe: '1H', indicators: [], chartType: 'line', algorithm: 'None' },
]

const DashboardPage = () => {
  const [charts, setCharts] = useState<ChartConfig[]>(initialCharts);
  const [activeChartId, setActiveChartId] = useState<string>(initialCharts[0].id);

  const activeChart = charts.find(c => c.id === activeChartId) || charts[0];

  const handleConfigChange = (newConfig: Partial<ChartConfig>) => {
    setCharts(charts.map(chart =>
      chart.id === activeChartId ? { ...chart, ...newConfig } : chart
    ));
  };

  const addChart = () => {
    const newChartId = `chart-${charts.length + 1}`;
    const newChart: ChartConfig = {
      id: newChartId,
      name: `Chart ${charts.length + 1}`,
      symbol: 'TSLA',
      timeframe: '1D',
      indicators: [],
      chartType: 'area',
      algorithm: 'None',
    };
    setCharts([...charts, newChart]);
    setActiveChartId(newChartId);
  }

  return (
    <div className="h-full flex flex-col">
        <div className="flex items-center mb-4">
            <h1 className="text-2xl font-bold">Dashboard</h1>
        </div>
        <MarketOverview />
        <div className="flex-1 flex flex-col mt-4">
          <Tabs value={activeChartId} onValueChange={setActiveChartId} className="flex-1 flex flex-col">
            <div className="flex items-center justify-between">
              <TabsList>
                {charts.map(chart => (
                  <TabsTrigger key={chart.id} value={chart.id}>{chart.name}</TabsTrigger>
                ))}
              </TabsList>
              <Button size="sm" variant="outline" onClick={addChart}>
                <Plus className="mr-2 h-4 w-4" />
                Add Chart
              </Button>
            </div>
            
            <TabsContent value={activeChartId} className="flex-1 mt-4">
                <ResizablePanelGroup direction="horizontal" className="rounded-lg border h-full">
                <ResizablePanel defaultSize={70}>
                    <div className="flex h-full items-center justify-center p-6">
                        <StockChart 
                          symbol={activeChart.symbol} 
                          timeframe={activeChart.timeframe} 
                          indicators={activeChart.indicators}
                          chartType={activeChart.chartType}
                          algorithm={activeChart.algorithm}
                        />
                    </div>
                </ResizablePanel>
                <ResizableHandle withHandle />
                <ResizablePanel defaultSize={30}>
                    <ChartConfigurationPanel config={activeChart} onConfigChange={handleConfigChange} />
                </ResizablePanel>
                </ResizablePanelGroup>
            </TabsContent>
          </Tabs>
        </div>
    </div>
  );
};

export default DashboardPage;
