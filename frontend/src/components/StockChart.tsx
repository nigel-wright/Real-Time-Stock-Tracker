
import { AreaChart, Area, LineChart, Line, BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, ReferenceDot } from 'recharts';
import { chartData } from '@/lib/mock-data';
import type { ChartType, Indicator, Algorithm } from '@/components/ChartConfigurationPanel';

const StockChart = ({ symbol, timeframe, indicators, chartType, algorithm }: { 
  symbol: string, 
  timeframe: string, 
  indicators: Indicator[],
  chartType: ChartType,
  algorithm: Algorithm
}) => {
  console.log(`Rendering chart for ${symbol} with timeframe ${timeframe}, type: ${chartType}, algorithm: ${algorithm}, and indicators: ${indicators.join(', ') || 'none'}`);
  
  const getSignals = (algo: Algorithm) => {
    switch(algo) {
      case 'Momentum':
        return [
          { name: 'Mar', value: chartData.find(d => d.name === 'Mar')?.value, type: 'buy' },
          { name: 'Jun', value: chartData.find(d => d.name === 'Jun')?.value, type: 'sell' },
        ];
      case 'MeanReversion':
        return [
          { name: 'Feb', value: chartData.find(d => d.name === 'Feb')?.value, type: 'sell' },
          { name: 'Apr', value: chartData.find(d => d.name === 'Apr')?.value, type: 'buy' },
          { name: 'Jul', value: chartData.find(d => d.name === 'Jul')?.value, type: 'sell' },
        ];
      default:
        return [];
    }
  }

  const signals = getSignals(algorithm).filter(s => s.value !== undefined);

  const commonProps = {
    data: chartData,
    margin: {
      top: 5,
      right: 30,
      left: 0,
      bottom: 0,
    }
  };

  const RenderSignals = () => (
    <>
      {signals.map((signal, index) => (
        <ReferenceDot
          key={index}
          x={signal.name}
          y={signal.value!}
          r={5}
          fill={signal.type === 'buy' ? '#22c55e' : '#ef4444'}
          stroke="hsl(var(--background))"
          strokeWidth={2}
          ifOverflow="extendDomain"
        />
      ))}
    </>
  );

  const CommonComponents = () => (
    <>
      <CartesianGrid strokeDasharray="3 3" stroke="hsl(var(--border) / 0.5)" />
      <XAxis dataKey="name" stroke="hsl(var(--muted-foreground))" />
      <YAxis stroke="hsl(var(--muted-foreground))" domain={['dataMin - 10', 'dataMax + 10']} />
      <Tooltip 
        contentStyle={{ 
          backgroundColor: 'hsl(var(--background))', 
          border: '1px solid hsl(var(--border))' 
        }}
      />
    </>
  );

  const renderChart = () => {
    switch (chartType) {
      case 'line':
        return (
          <LineChart {...commonProps}>
            <CommonComponents />
            <Line type="monotone" dataKey="value" stroke="hsl(var(--primary))" strokeWidth={2} dot={false} />
            <RenderSignals />
          </LineChart>
        );
      case 'bar':
        return (
          <BarChart {...commonProps}>
            <CommonComponents />
            <Bar dataKey="value" fill="hsl(var(--primary))" />
            <RenderSignals />
          </BarChart>
        );
      case 'area':
      default:
        return (
          <AreaChart {...commonProps}>
            <defs>
              <linearGradient id="colorValue" x1="0" y1="0" x2="0" y2="1">
                <stop offset="5%" stopColor="hsl(var(--primary))" stopOpacity={0.8}/>
                <stop offset="95%" stopColor="hsl(var(--primary))" stopOpacity={0}/>
              </linearGradient>
            </defs>
            <CommonComponents />
            <Area type="monotone" dataKey="value" stroke="hsl(var(--primary))" strokeWidth={2} fillOpacity={1} fill="url(#colorValue)" />
            <RenderSignals />
          </AreaChart>
        );
    }
  }

  return (
    <ResponsiveContainer width="100%" height="100%">
      {renderChart()}
    </ResponsiveContainer>
  );
};

export default StockChart;
